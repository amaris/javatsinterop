var BubbleTreeController = (function () {
    function BubbleTreeController() {
        var tree = new BubbleTree();
        tree.build({
            container: document.getElementById("bubbletree"),
            url: "/home/tree",
            //url: "/data.json",
            handlers: {
                "click": function (d) {
                    console.info(">> " + d.data.weight);
                    console.info(">> " + d.data.weight);
                },
                "mouseover": function (d) { console.info("mouseover: " + d.data.test); },
                "dblclick": function (d) { console.info("dblclick"); }
            },
            showRoot: false
        });
    }
    return BubbleTreeController;
}());
//# sourceMappingURL=bubbletreeController.js.map