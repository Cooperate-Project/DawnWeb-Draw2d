ClassShape = draw2d.shape.layout.VerticalLayout.extend({

    NAME: "ClassShape",
    attributeCompartment: new AttributeCompartment(),
    operationCompartment: new OperationCompartment(),

    init: function (attr) {
        this._super($.extend({bgColor: "#ffffff", color: "#000000", stroke: 1, radius: 0}, attr));

        this.classLabel = new draw2d.shape.basic.Label({
            text: "ClassName",
            stroke: 1,
            fontColor: "#000000",
            bgColor: "#f7f7f7",
            radius: this.getRadius(),
            padding: 5,
            resizeable: true,
            editor: new draw2d.ui.LabelInplaceEditor()
        });

        this.attributeCompartment = new AttributeCompartment();
        this.operationCompartment = new OperationCompartment();

        var _table = this;
        this.classLabel.on("contextmenu", function (emitter, event) {
            $.contextMenu({
                selector: 'body',
                events: {
                    hide: function () {
                        $.contextMenu('destroy');
                    }
                },
                callback: $.proxy(function (key, options) {
                    switch (key) {
                        case "newAtt":
                            setTimeout(function () {
                                _table.attributeCompartment.addAttribute("_new_").onDoubleClick();
                            }, 10);
                            break;
                        case "newOp":
                            setTimeout(function () {
                                _table.operationCompartment.addOperation("_new_").onDoubleClick();
                            }, 10);
                            break;
                        case "delete":
                            var cmd = new draw2d.command.CommandDelete(_table);
                            emitter.getCanvas().getCommandStack().execute(cmd);
                            break;
                        default:
                            break;
                    }

                }, this),
                x: event.x,
                y: event.y,
                items: {
                    "newAtt": {name: "New Attribute"},
                    "newOp": {name: "New Operation"},
                    "delete": {name: "Delete Class"}
                }
            });
        });

        this.add(this.classLabel);
        this.add(this.attributeCompartment);
        this.add(this.operationCompartment);
        var hport = this.createPort("hybrid");
        hport.setName("port_" + this.getId());
        hport.setConnectionAnchor(new draw2d.layout.anchor.ChopboxConnectionAnchor(this));

    },


    /**
     * @method
     * Set the name of the DB table. Visually it is the header of the shape
     *
     * @param name
     */
    setName: function (name) {
        this.classLabel.setText(name);

        return this;
    },

    getAttributes: function () {
        return this.attributeCompartment;
    },

    getOperations: function () {
        return this.operationCompartment;
    },

    /**
     * @method
     * Return an objects with all important attributes for XML or JSON serialization
     *
     * @returns {Object}
     */
    getPersistentAttributes: function () {
        var memento = {type: "ClassShape"};

        memento.x = this.x;
        memento.y = this.y;
        memento.id = this.id;

        memento.name = this.classLabel.getText();
        memento.attributeCompartment = this.attributeCompartment.getPersistentAttributes();
        memento.operationCompartment = this.operationCompartment.getPersistentAttributes();

        return memento;
    },

    /**
     * @method
     * Read all attributes from the serialized properties and transfer them into the shape.
     *
     * @param {Object} memento
     * @return
     */
    setPersistentAttributes: function (memento) {
        this._super(memento);

        this.setName(memento.name);

        if (typeof memento.attributeCompartment !== "undefined") {
            this.attributeCompartment.setPersistentAttributes(memento);
        }

        if (typeof memento.operationCompartment !== "undefined") {
            this.operationCompartment.setPersistentAttributes(memento);
        }

        return this;
    }

});
