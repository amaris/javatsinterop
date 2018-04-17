/*
 * MIT License
 *
 * Copyright (c) 2018 Amaris <rpawlak@amaris.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
var ExampleResource = com.amaris.javatsinterop.api.ExampleResource;
var ExampleController = (function () {
    function ExampleController() {
        var _this = this;
        var example = new ExampleResource();
        example.salaries(null, null, function (salaries) {
            _this.table = new Table();
            _this.table.build({
                container: document.getElementById("table"),
                data: salaries,
                headerClickHandler: function (column) {
                    console.info("click1 > " + column + "-" + Object.keys(salaries[0])[column]);
                    _this.table.loadData(_this.table.getData().sort(function (a, b) {
                        return a[Object.keys(salaries[0])[column]] - b[Object.keys(salaries[0])[column]];
                    }));
                }
            });
        });
        example.tree(function (rootNode) {
            _this.tree = new BubbleTree();
            _this.tree.build({
                container: document.getElementById("bubbletree"),
                data: rootNode,
                handlers: {
                    "click": function (d) {
                        _this.tree.clearSelect();
                        _this.tree.select(d.data.uid);
                        var rank = null, discipline = null;
                        switch (d.depth) {
                            case 0:
                                break;
                            case 1:
                                rank = _this.getRankName(d.data.name);
                                break;
                            case 2:
                                rank = _this.getRankName(d.parent.data.name);
                                discipline = d.data.name;
                                break;
                        }
                        console.info("fetching salaries: " + rank + ", " + discipline);
                        example.salaries(rank, discipline, function (salaries) {
                            _this.table.loadData(salaries);
                        });
                    },
                    "mouseover": function (d) { console.info("mouseover: " + d.data); },
                    "dblclick": function (d) { console.info("dblclick"); }
                },
                showRoot: true
            });
        });
    }
    ExampleController.prototype.getRankName = function (nodeName) {
        switch (nodeName) {
            case "Assistant Professor":
                return "AsstProf";
            case "Associate Professor":
                return "AssocProf";
            case "Professor":
                return "Prof";
        }
    };
    return ExampleController;
}());
//# sourceMappingURL=exampleController.js.map