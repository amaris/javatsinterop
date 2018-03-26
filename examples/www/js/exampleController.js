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
        var example = new ExampleResource();
        example.salaries(function (salaries) {
            var table = new Table();
            table.build({
                container: document.getElementById("table"),
                data: salaries
            });
        });
        example.tree(function (rootNode) {
            var tree = new BubbleTree();
            tree.build({
                container: document.getElementById("bubbletree"),
                data: rootNode,
                handlers: {
                    "click": function (d) {
                        console.info(">> " + d.data.weight);
                        //test.helloWorld((s) => { console.info("ASYNC >> " + s); })
                    },
                    "mouseover": function (d) { console.info("mouseover: " + d.data.test); },
                    "dblclick": function (d) { console.info("dblclick"); }
                },
                showRoot: false
            });
        });
    }
    return ExampleController;
}());
//# sourceMappingURL=exampleController.js.map