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

import GenericNode = com.amaris.javatsinterop.dto.GenericNode;
import Salary = com.amaris.javatsinterop.dto.Salary;
import ExampleResource = com.amaris.javatsinterop.api.ExampleResource;

class ExampleController {

    table: Table<Salary>;
    tree: BubbleTree<GenericNode>;

    constructor() {
        let example: ExampleResource = new ExampleResource();

        example.salaries(null, null, salaries => {
            this.table = new Table<Salary>();
            this.table.build({
                container: document.getElementById("table"),
                data: salaries,
                headerClickHandler: (column) => {
                    console.info("click1 > " + column + "-" + Object.keys(salaries[0])[column]);
                    this.table.loadData(this.table.getData().sort((a, b) => {
                        return a[Object.keys(salaries[0])[column]] - b[Object.keys(salaries[0])[column]];
                    }));
                }
            });
        });

        example.tree(rootNode => {
            this.tree = new BubbleTree<GenericNode>();
            this.tree.build({
                container: document.getElementById("bubbletree"),
                data: rootNode,
                handlers: {
                    "click": d => {
                        this.tree.clearSelect();
                        this.tree.select(d.data.uid);

                        let rank = null, discipline = null;
                        switch (d.depth) {
                            case 0:
                                break;
                            case 1:
                                rank = this.getRankName(d.data.name);
                                break;
                            case 2:
                                rank = this.getRankName(d.parent.data.name);
                                discipline = d.data.name;
                                break;
                        }
                        console.info("fetching salaries: " + rank + ", " + discipline);
                        example.salaries(rank, discipline, salaries => {
                            this.table.loadData(salaries);
                        });
                    },
                    "mouseover": function(d) { console.info("mouseover: " + d.data); },
                    "dblclick": function(d) { console.info("dblclick"); }
                },
                showRoot: true
            });
        });
    }

    private getRankName(nodeName: string): string {
        switch (nodeName) {
            case "Assistant Professor":
                return "AsstProf";
            case "Associate Professor":
                return "AssocProf";
            case "Professor":
                return "Prof";
        }
    }

}