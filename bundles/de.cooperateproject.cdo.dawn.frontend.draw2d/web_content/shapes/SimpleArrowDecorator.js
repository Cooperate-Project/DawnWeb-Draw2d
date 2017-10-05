draw2d.decoration.connection.SimpleArrowDecorator = draw2d.decoration.connection.Decorator.extend({

    NAME : "draw2d.decoration.connection.SimpleArrowDecorator",

    init: function(width, height)
    {
        this._super( width, height);
    },

    paint: function(paper)
    {
        var st = paper.set();

        st.push(paper.path(["M0 0" ,
            "L", this.width, " ", -this.height/2
            ].join("")));
        st.push(paper.path([
            "M", this.width, " ",  this.height/2,
            "L0 0"].join("")));

        st.attr({fill:this.backgroundColor.hash(),stroke:this.color.hash()});

        return st;
    }
});