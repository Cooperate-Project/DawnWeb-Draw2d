ListCompartment = draw2d.shape.layout.VerticalLayout.extend({

    NAME: "ListCompartment",

    init: function (attr) {
        this._super($.extend({bgColor: "#FFFFFF", color: "#000000", stroke: 1, radius: 3}, attr));
        //ListItemLabel
    },

    getMinHeight: function () {
        var calcHeight = this._super();

        return Math.max(20, calcHeight);
    }


});
