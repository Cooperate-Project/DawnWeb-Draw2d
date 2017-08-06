var testShape = draw2d.SetFigure.extend({

    NAME: "testShape",

    init:function(attr, setter, getter)
    {
        this._super( $.extend({stroke:0, bgColor:null, width:201,height:211},attr), setter, getter);
        var port;
        this.persistPorts=false;
    },

    createShapeElement : function()
    {
        var shape = this._super();
        this.originalWidth = 201;
        this.originalHeight= 211;
        return shape;
    },

    createSet: function()
    {
        this.canvas.paper.setStart();

        // BoundingBox
        shape = this.canvas.paper.path("M0,0 L201,0 L201,211 L0,211");
        shape.attr({"stroke":"none","stroke-width":0,"fill":"none"});
        shape.data("name","BoundingBox");

        // PropRec
        shape = this.canvas.paper.path('M0 43L201 43L201 128L0 128Z');
        shape.attr({"stroke":"#303030","stroke-width":1,"fill":"#FFFFFF","dasharray":null,"opacity":1});
        shape.data("name","PropRec");

        // MethodRec
        shape = this.canvas.paper.path('M201 211L0 211L0 126L201 126Z');
        shape.attr({"stroke":"#303030","stroke-width":1,"fill":"#FFFFFF","dasharray":null,"opacity":1});
        shape.data("name","MethodRec");

        // TitleRec
        shape = this.canvas.paper.path('M0 0L201 0L201 42L0 42Z');
        shape.attr({"stroke":"#303030","stroke-width":1,"fill":"#FFFFFF","dasharray":null,"opacity":1});
        shape.data("name","TitleRec");

        // title
        shape = this.canvas.paper.text(0,0,'Title');
        shape.attr({"x":9,"y":18,"text-anchor":"start","text":"Title","font-family":"\"Arial\"","font-size":20,"stroke":"none","fill":"#080808","stroke-scale":true,"font-weight":"normal","stroke-width":0,"opacity":1});
        shape.data("name","title");

        // prop1
        shape = this.canvas.paper.text(0,0,'Property');
        shape.attr({"x":9,"y":57,"text-anchor":"start","text":"Property","font-family":"\"Arial\"","font-size":16,"stroke":"none","fill":"#080808","stroke-scale":true,"font-weight":"normal","stroke-width":0,"opacity":1});
        shape.data("name","prop1");

        // prop2
        shape = this.canvas.paper.text(0,0,'Property');
        shape.attr({"x":9,"y":81.5,"text-anchor":"start","text":"Property","font-family":"\"Arial\"","font-size":16,"stroke":"none","fill":"#080808","stroke-scale":true,"font-weight":"normal","stroke-width":0,"opacity":1});
        shape.data("name","prop2");

        // prop3
        shape = this.canvas.paper.text(0,0,'Property');
        shape.attr({"x":9,"y":105,"text-anchor":"start","text":"Property","font-family":"\"Arial\"","font-size":16,"stroke":"none","fill":"#080808","stroke-scale":true,"font-weight":"normal","stroke-width":0,"opacity":1});
        shape.data("name","prop3");

        // method1
        shape = this.canvas.paper.text(0,0,'Method');
        shape.attr({"x":9,"y":142.5,"text-anchor":"start","text":"Method","font-family":"\"Arial\"","font-size":16,"stroke":"none","fill":"#080808","stroke-scale":true,"font-weight":"normal","stroke-width":0,"opacity":1});
        shape.data("name","method1");

        // method2
        shape = this.canvas.paper.text(0,0,'Method');
        shape.attr({"x":9,"y":165,"text-anchor":"start","text":"Method","font-family":"\"Arial\"","font-size":16,"stroke":"none","fill":"#080808","stroke-scale":true,"font-weight":"normal","stroke-width":0,"opacity":1});
        shape.data("name","method2");

        // method3
        shape = this.canvas.paper.text(0,0,'Method');
        shape.attr({"x":9,"y":187.928,"text-anchor":"start","text":"Method","font-family":"\"Arial\"","font-size":16,"stroke":"none","fill":"#080808","stroke-scale":true,"font-weight":"normal","stroke-width":0,"opacity":1});
        shape.data("name","method3");


        return this.canvas.paper.setFinish();
    },

    applyAlpha: function()
    {
    },

    layerGet: function(name, attributes)
    {
        if(this.svgNodes===null) return null;

        var result=null;
        this.svgNodes.some(function(shape){
            if(shape.data("name")===name){
                result=shape;
            }
            return result!==null;
        });

        return result;
    },

    layerAttr: function(name, attributes)
    {
        if(this.svgNodes===null) return;

        this.svgNodes.forEach(function(shape){
            if(shape.data("name")===name){
                shape.attr(attributes);
            }
        });
    },

    layerShow: function(name, flag, duration)
    {
        if(this.svgNodes===null) return;

        if(duration){
            this.svgNodes.forEach(function(node){
                if(node.data("name")===name){
                    if(flag){
                        node.attr({ opacity : 0 }).show().animate({ opacity : 1 }, duration);
                    }
                    else{
                        node.animate({ opacity : 0 }, duration, function () { this.hide() });
                    }
                }
            });
        }
        else{
            this.svgNodes.forEach(function(node){
                if(node.data("name")===name){
                    if(flag){node.show();}
                    else{node.hide();}
                }
            });
        }
    },

    calculate: function()
    {
    },

    onStart: function()
    {
    },

    onStop:function()
    {
    },

    getParameterSettings: function()
    {
        return [];
    },

    /**
     * @method
     */
    addPort: function(port, locator)
    {
        this._super(port, locator);
        return port;
    },

    /**
     * @method
     * Return an objects with all important attributes for XML or JSON serialization
     *
     * @returns {Object}
     */
    getPersistentAttributes : function()
    {
        var memento = this._super();

        // add all decorations to the memento
        //
        memento.labels = [];
        this.children.each(function(i,e){
            var labelJSON = e.figure.getPersistentAttributes();
            labelJSON.locator=e.locator.NAME;
            memento.labels.push(labelJSON);
        });

        return memento;
    },

    /**
     * @method
     * Read all attributes from the serialized properties and transfer them into the shape.
     *
     * @param {Object} memento
     * @returns
     */
    setPersistentAttributes : function(memento)
    {
        this._super(memento);

        // remove all decorations created in the constructor of this element
        //
        this.resetChildren();

        // and add all children of the JSON document.
        //
        $.each(memento.labels, $.proxy(function(i,json){
            // create the figure stored in the JSON
            var figure =  eval("new "+json.type+"()");

            // apply all attributes
            figure.attr(json);

            // instantiate the locator
            var locator =  eval("new "+json.locator+"()");

            // add the new figure as child to this figure
            this.add(figure, locator);
        },this));
    }
});

/**
 * by 'Draw2D Shape Designer'
 *
 * Custom JS code to tweak the standard behaviour of the generated
 * shape. add your custome code and event handler here.
 *
 *
 */
testShape = testShape.extend({

    init: function(attr, setter, getter){
        this._super(attr, setter, getter);

        // your special code here
    },

    /**
     *  Called by the simulator for every calculation
     *  loop
     *  @required
     **/
    calculate:function()
    {
    },


    /**
     *  Called if the simulation mode is starting
     *  @required
     **/
    onStart:function()
    {
    },

    /**
     *  Called if the simulation mode is stopping
     *  @required
     **/
    onStop:function()
    {
    }
});