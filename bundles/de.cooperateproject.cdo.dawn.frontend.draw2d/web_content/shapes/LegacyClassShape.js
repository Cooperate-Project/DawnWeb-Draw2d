LegacyClassShape = draw2d.shape.layout.VerticalLayout.extend({

    NAME: "LegacyClassShape",
    propertyCount: 0,
    functionCount: 0,

    init: function (attr) {
        this._super($.extend({
            bgColor: "#f7f7f7",
            color: "#d7d7d7",
            stroke: 1,
            strokeColor: "#000000",
            radius: 0
        }, attr));

        this.classLabel = new draw2d.shape.basic.Label({
            text: "ClassName",
            stroke: 0,
            fontColor: "#333333",
            bgColor: "#dddddd",
            fontSize: 14,
            bold: true,
            radius: this.getRadius(),
            padding: 10,
            resizeable: true,
            editor: new draw2d.ui.LabelInplaceEditor()
        });

        this.add(this.classLabel);

        this.addSeparator();
        this.addSeparator();

        var hport = this.createPort("hybrid");
        hport.setName("port_" + this.getId());
        hport.setConnectionAnchor(new draw2d.layout.anchor.ChopboxConnectionAnchor(this));
    },

    addProperty: function (txt) {
        this.addEntity(txt, "PROPERTY");
    },

    addFunction: function (txt) {
        this.addEntity(txt, "FUNCTION");
    },

    addSeparator: function () {
        var label = new draw2d.shape.basic.Label({
            text: "",
            stroke: 0,
            radius: 0,
            bgColor: "#6f6f6f",
            padding: {left: 10, top: 0, right: 10, bottom: 1},
            height: 1,
            resizeable: true,
            userData: "SPLIT"
        });

        this.add(label);
    },

    /**
     * @method
     * Add an entity to the db shape
     *
     * @param {String} txt the label to show
     */
    addEntity: function (txt, uml) {

        var optionalIndex = "";

        if (uml == "PROPERTY") {
            this.propertyCount += 1;
            optionalIndex = this.propertyCount;
        } else if (uml == "FUNCTION") {
            this.functionCount += 1;
            optionalIndex = this.propertyCount + this.functionCount + 1;
        }

        var label = new draw2d.shape.basic.Label({
            text: txt,
            stroke: 0,
            radius: 0,
            bgColor: null,
            padding: {left: 10, top: 3, right: 10, bottom: 5},
            fontColor: "#4a4a4a",
            resizeable: true,
            userData: uml,
            editor: new draw2d.ui.LabelInplaceEditor()
        });

//        label.installEditor(new draw2d.ui.LabelEditor());
        // var input = label.createPort("input");
        //var output= label.createPort("output");

        //input.setName("input_"+label.id);
        // output.setName("output_"+label.id);

        var _table = this;
        label.on("contextmenu", function (emitter, event) {
            $.contextMenu({
                selector: 'body',
                events: {
                    hide: function () {
                        $.contextMenu('destroy');
                    }
                },
                callback: $.proxy(function (key, options) {
                    switch (key) {
                        case "rename":
                            setTimeout(function () {
                                emitter.onDoubleClick();
                            }, 10);
                            break;
                        case "new":
                            setTimeout(function () {
                                _table.addEntity("_new_").onDoubleClick();
                            }, 10);
                            break;
                        case "delete":
                            // with undo/redo support
                            var cmd = new draw2d.command.CommandDelete(emitter);
                            emitter.getCanvas().getCommandStack().execute(cmd);
                        default:
                            break;
                    }

                }, this),
                x: event.x,
                y: event.y,
                items: {
                    "rename": {name: "Rename"},
                    "new": {name: "New Entity"},
                    "sep1": "---------",
                    "delete": {name: "Delete"}
                }
            });
        });

        if ($.isNumeric(optionalIndex)) {
            this.add(label, null, optionalIndex + 1);
        }
        else {
            this.add(label);
        }

        return label;
    },

    /**
     * @method
     * Remove the entity with the given index from the DB table shape.<br>
     * This method removes the entity without care of existing connections. Use
     * a draw2d.command.CommandDelete command if you want to delete the connections to this entity too
     *
     * @param {Number} index the index of the entity to remove
     */
    removeEntity: function (index) {
        this.remove(this.children.get(index + 1).figure);
    },

    /**
     * @method
     * Returns the entity figure with the given index
     *
     * @param {Number} index the index of the entity to return
     */
    getEntity: function (index) {
        return this.children.get(index + 1).figure;
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


    /**
     * @method
     * Return an objects with all important attributes for XML or JSON serialization
     *
     * @returns {Object}
     */
    getPersistentAttributes: function () {
        var memento = this._super();

        memento.name = this.classLabel.getText();
        memento.entities = [];
        this.children.each(function (i, e) {

            if (i > 0) { // skip the header of the figure
                if (e.figure.userData == "FUNCTION" || e.figure.userData == "PROPERTY") {
                    memento.entities.push({
                        text: e.figure.getText(),
                        id: e.figure.id,
                        umlType: e.figure.userData
                    });
                }
            }
        });

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

        if (typeof memento.entities !== "undefined") {
            $.each(memento.entities, $.proxy(function (i, e) {
                var entity = this.addEntity(e.text, e.umlType);
                entity.id = e.id;
                entity.getInputPort(0).setName("input_" + e.id);
                entity.getOutputPort(0).setName("output_" + e.id);
            }, this));
        }

        return this;
    }

});
