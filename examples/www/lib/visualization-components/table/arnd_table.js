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
     * Builds the buble tree diagram as specified by the given configuration.
     *
     * @param {BubbleTreeConfiguration} config - the configuration
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
        //        // create a cell in each row for each column
        //        var cells = rows.selectAll('td')
        //          .data(function (row) {
        //            return columns.map(function (column) {
        //              return {column: column, value: row[column]};
        //            });
        //          })
        //          .enter()
        //          .append('td')
        //            .text(function (d) { return d.value; });
        //      return table;
    };
    return Table;
}());
//# sourceMappingURL=arnd_table.js.map