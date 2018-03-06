

import GenericNode = com.amaris.javatsinterop.dto.GenericNode;

class BubbleTreeController {
    constructor() {
        let tree : BubbleTree<GenericNode> = new BubbleTree<GenericNode>();
        tree.build({
            container: document.getElementById("bubbletree"),
            url: "/home/tree",
            //url: "/data.json",
            handlers: {
                "click": function(d) {
                    console.info(">> " + d.data.weight);
                },
                "mouseover": function(d) { console.info("mouseover: "+d.data.test); },
                "dblclick": function(d) { console.info("dblclick"); }
            },
            showRoot: false
        });
    }
}