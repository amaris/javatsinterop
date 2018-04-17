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
var tableExample;
(function (tableExample) {
    var ExampleResource = com.amaris.javatsinterop.api.ExampleResource;
    var Controller = (function () {
        function Controller() {
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
        }
        return Controller;
    }());
    tableExample.Controller = Controller;
})(tableExample || (tableExample = {}));
//# sourceMappingURL=tableExampleController.js.map