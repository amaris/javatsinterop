/*
 * Visualisation Components - https://github.com/amaris/visualization-components
 * Copyright (C) 2018 Amaris <rpawlak@amaris.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
/**
 * An interactive D3.js component to render trees as an SVG flat bubble tree.
 */
var BubbleTree = (function () {
    function BubbleTree() {
        this.selections = {};
    }
    BubbleTree.prototype.update = function () {
        this.diameter = Math.min(this.config.container.clientWidth, this.config.container.clientHeight);
        this.width = this.config.container.clientWidth;
        this.height = this.config.container.clientHeight;
        if (NaN === this.diameter || this.diameter <= 0) {
            this.diameter = 1000;
            this.width = 1000;
            this.height = 1000;
        }
    };
    BubbleTree.prototype.buildFromData = function (rootData) {
        var _this = this;
        this.rootData = rootData;
        var root = d3.hierarchy(rootData)
            .sum(function (d) { return d["weight"]; })
            .sort(function (a, b) { return b.value - a.value; });
        this.focus = root;
        var nodes = this.pack(root).descendants();
        this.circle = this.g.selectAll("circle")
            .data(nodes)
            .enter().append("circle").each(function (d) { if (d.data.uid == null)
            d.data.uid = "__generated_" + (BubbleTree.ID++); })
            .attr("class", function (d) { return d.parent ? d.children ? "node" : "node node--leaf" : "node node--root"; })
            .attr("id", function (d) { return d.data.uid ? "circle_" + d.data.uid : null; })
            .style("display", function (d) { return !d.parent ? _this.config.showRoot ? "inline" : "none" : "inline"; })
            .style("stroke", "#B0B0B0")
            .style("fill", function (d) { return _this.nodeColor(d); });
        var handlers = {
            "click": function (d) {
                if (!d.children) {
                    if (_this.config.selectOnClick) {
                        _this.clearSelect().select(d.data.uid);
                    }
                    if (_this.config.onClick !== undefined) {
                        _this.config.onClick(d);
                    }
                    else {
                        _this.zoom(d.parent);
                    }
                    d3.event.stopPropagation();
                }
                else if (_this.focus !== d) {
                    _this.zoom(d);
                    d3.event.stopPropagation();
                }
            },
            "mouseover": function (d) {
                if (d != _this.focus) {
                    _this.showText(d, true);
                    while (d.parent != null /*&& d.parent!=this.focus*/) {
                        _this.showText(d.parent, false);
                        d = d.parent;
                    }
                }
            },
            "mouseout": function (d) {
                if (d.parent !== _this.focus) {
                    _this.showText(d, false);
                }
            }
        };
        // merge handlers
        var _loop_1 = function(userHandler) {
            if (handlers[userHandler]) {
                var handler_1 = handlers[userHandler];
                // merge with user-defined handler
                handlers[userHandler] = function (d) {
                    handler_1(d);
                    _this.config.handlers[userHandler](d);
                };
            }
            else {
                // install user handler
                handlers[userHandler] =
                    this_1.config.handlers[userHandler];
            }
        };
        var this_1 = this;
        for (var userHandler in this.config.handlers) {
            _loop_1(userHandler);
        }
        // apply all handlers
        for (var handler in handlers) {
            console.info("installing handler " + handler);
            this.circle.on(handler, handlers[handler]);
        }
        this.g.selectAll("text")
            .data(nodes)
            .enter().append("text")
            .attr("id", function (d) { return d.data.uid ? "text_" + d.data.uid : null; })
            .style("fill-opacity", function (d) { return d.parent === root ? 1 : 0; })
            .style("display", function (d) { return d.parent === root ? "inline" : "none"; })
            .style("pointer-events", "none")
            .style("font", "15px 'Helvetica Neue', Helvetica, Arial, sans-serif")
            .style("text-anchor", "middle")
            .style("text-shadow", "0 1px 0 #fff, 1px 0 0 #fff, -1px 0 0 #fff, 0 -1px 0 #fff")
            .text(function (d) { return d.data.name; });
        this.svg.on("click", function () { return _this.zoom(root); });
        this.zoomTo([root.x, root.y, root.r * 2 + this.config.margin]);
        if (this.config.onBuilt) {
            this.config.onBuilt(this);
        }
    };
    /**
     * Builds the buble tree diagram as specified by the given configuration.
     *
     * @param {BubbleTreeConfiguration} config - the configuration
     */
    BubbleTree.prototype.build = function (config) {
        var _this = this;
        this.config = config;
        this.config.container.innerHTML = "";
        this.config.container.setAttribute("width", "100%");
        this.config.container.setAttribute("height", "100%");
        if (!this.config.handlers) {
            this.config.handlers = {};
        }
        this.config.showRoot = this.config.showRoot ? this.config.showRoot : false;
        this.config.baseLeafColorHue = this.config.baseLeafColorHue ? this.config.baseLeafColorHue : 70;
        this.svg = d3.select(config.container);
        if (!this.config.margin)
            this.config.margin = 20;
        this.update();
        console.info("diameter: " + this.diameter);
        this.g = this.svg.append("g").attr("transform", "translate(" + this.width / 2 + "," + this.height / 2 + ")");
        this.defaultColor = d3.scaleLinear()
            .domain([-1, 5])
            .range(["hsl(197,30%,98%)", "hsl(220,50%,88%)"])
            .interpolate(d3.interpolateHcl);
        this.pack = d3.pack()
            .size([this.diameter - this.config.margin, this.diameter - this.config.margin])
            .padding(2);
        // use possible url field for backward compatibility
        if (config.data == null && config['url'] != null) {
            config.data = config['url'];
        }
        if (typeof config.data === 'string') {
            // URL case
            d3.json(config.data, function (error, rootData) {
                console.log(rootData);
                if (error)
                    throw error;
                _this.buildFromData(rootData);
            });
        }
        else {
            // data as JavaScript object
            this.buildFromData(config.data);
        }
    };
    BubbleTree.prototype.leafColor = function (saturation) {
        return "hsl(" + this.config.baseLeafColorHue + "," + (saturation * 100) + "%,70%)";
    };
    BubbleTree.prototype.nodeColor = function (d) {
        return d.data.color ? d.data.color : d.children ? this.defaultColor(d.depth) : this.leafColor(0);
    };
    /**
     * Zooms to a node represented by its uid.
     *
     * @param {string} uid - the uid of the node to be zoomed to
     */
    BubbleTree.prototype.zoomToId = function (uid) {
        this.zoom(d3.select("#circle_" + uid).datum());
        return this;
    };
    /**
     * Selects a node represented by its uid. The weight will determine the intensity of the selection color (0 to 1).
     *
     * @param {string} uid - the uid of the node to be zoomed to
     * @param {number} weight - the selection's weight (color intensity)
     */
    BubbleTree.prototype.select = function (uid, weight) {
        var _this = this;
        if (weight === void 0) { weight = 1; }
        this.selections[uid] = weight;
        this.g.selectAll("circle")
            .filter(function (d) { return d.data.uid in _this.selections; })
            .classed("selected", true)
            .style("fill", function (d) { return _this.leafColor(_this.selections[d.data.uid]); });
        return this;
    };
    /**
     * Clears all the selections.
     *
     * @param {string} uid - the uid of the node to be zoomed to
     * @see #select
     */
    BubbleTree.prototype.clearSelect = function () {
        var _this = this;
        this.g.selectAll("circle")
            .filter(function (d) { return d.data.uid in _this.selections; })
            .classed("selected", false)
            .style("fill", function (d) { return _this.nodeColor(d); });
        this.selections = {};
        return this;
    };
    BubbleTree.prototype.zoomTo = function (v) {
        var k = this.diameter / v[2];
        this.view = v;
        var node = this.g.selectAll("circle,text");
        node.attr("transform", function (d) { return "translate(" + (d.x - v[0]) * k + "," + (d.y - v[1]) * k + ")"; });
        this.circle.attr("r", function (d) { return d.r * k; });
    };
    BubbleTree.prototype.zoom = function (d) {
        var _this = this;
        this.focus = d;
        var transition = d3.transition()
            .duration(d3.event && d3.event.altKey ? 7500 : 750)
            .tween("zoom", function () {
            var i = d3.interpolateZoom(_this.view, [_this.focus.x, _this.focus.y, _this.focus.r * 2 + _this.config.margin]);
            return function (t) { return _this.zoomTo(i(t)); };
        });
        transition.select("#" + this.config.container.id).selectAll("text")
            .filter(function (d) { return d.parent && d.parent === _this.focus || _this.nodeToText(d).style.display === "inline"; })
            .style("fill-opacity", function (d) { return d.parent === _this.focus ? 1 : 0; })
            .on("start", function (d) { if (d.parent === _this.focus)
            _this.nodeToText(d).style.display = "inline"; })
            .on("end", function (d) { if (d.parent !== _this.focus)
            _this.nodeToText(d).style.display = "none"; });
    };
    /**
     * Returns the root data as read from the JSON.
     *
     * @see build
     */
    BubbleTree.prototype.getRootData = function () {
        return this.rootData;
    };
    BubbleTree.prototype.nodeToText = function (d) {
        return document.getElementById("text_" + d.data.uid);
    };
    BubbleTree.prototype.nodeToCircle = function (d) {
        return document.getElementById("circle_" + d.data.uid);
    };
    BubbleTree.prototype.showText = function (d, show) {
        if (show === void 0) { show = true; }
        var text = this.nodeToText(d);
        if (text) {
            text.style.fillOpacity = show ? "1" : "0";
            text.style.display = show ? "inline" : "none";
        }
    };
    BubbleTree.ID = 1;
    return BubbleTree;
}());
/*
 * Visualisation Components - https://github.com/amaris/visualization-components
 * Copyright (C) 2018 Amaris <rpawlak@amaris.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
/**
 * An interactive D3.js component to render objects in a table.
 */
var Table = (function () {
    function Table() {
    }
    /**
     * Builds the table as specified by the given configuration.
     *
     * @param {TableConfiguration} config - the configuration
     */
    Table.prototype.build = function (config) {
        var _this = this;
        this.data = config.data;
        this.config = config;
        this.config.container.innerHTML = "";
        this.selection = d3.select(config.container);
        var table = this.selection.append('table').classed('table', true);
        var thead = table.append('thead').classed('thead-light', true);
        var tbody = table.append('tbody');
        // append the header row
        thead.append('tr')
            .selectAll('th')
            .data(Object.keys(this.data[0])).enter()
            .append('th')
            .text(function (column) { return column; });
        // create a row for each object in the data
        var rows = tbody.selectAll('tr')
            .data(this.data)
            .enter()
            .append('tr');
        rows.selectAll('td')
            .data(function (d) {
            return Object.keys(_this.data[0]).map(function (k) {
                return { 'value': d[k], 'name': k };
            });
        }).enter()
            .append('td')
            .attr('data-th', function (d) {
            return d.name;
        })
            .text(function (d) {
            return d.value;
        });
    };
    return Table;
}());
//# sourceMappingURL=visualization-components-bundle.js.map