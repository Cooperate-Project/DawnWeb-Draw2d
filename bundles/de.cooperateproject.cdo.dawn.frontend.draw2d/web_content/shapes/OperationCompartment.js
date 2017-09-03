OperationCompartment = ListCompartment.extend({

    NAME: "OperationCompartment",

    init: function (attr) {
        this._super($.extend({stroke: 1, radius: 0}, attr));
    },


    /**
     * @method
     * Add an entity to the db shape
     *
     * @param {String} txt the label to show
     * @param {Number} [optionalIndex] index where to insert the entity
     */
    addOperation: function (txt, optionalIndex) {
        var label = new draw2d.shape.basic.Label({
            text: txt,
            stroke: 0,
            radius: 0,
            bgColor: null,
            padding: {left: 10, top: 3, right: 10, bottom: 5},
            fontColor: "#4a4a4a",
            resizeable: true,
            editor: new draw2d.ui.LabelEditor()
        });


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
                        case "new":
                            setTimeout(function () {
                                _table.addOperation("_new_").onDoubleClick();
                            }, 10);
                            break;
                        case "delete":
                            var cmd = new draw2d.command.CommandDelete(emitter);
                            emitter.getCanvas().getCommandStack().execute(cmd);
                            break;
                        default:
                            break;
                    }

                }, this),
                x: event.x,
                y: event.y,
                items: {
                    "new": {name: "New Operation"},
                    "delete": {name: "Delete Operation"}
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
    removeOperation: function (index) {
        this.remove(this.children.get(index + 1).figure);
    },

    /**
     * @method
     * Returns the entity figure with the given index
     *
     * @param {Number} index the index of the entity to return
     */
    getOperation: function (index) {
        return this.children.get(index + 1).figure;
    },


    /**
     * @method
     * Return an objects with all important attributes for XML or JSON serialization
     *
     * @returns {Object}
     */
    getPersistentAttributes: function () {
        var memento = {};

        memento.operations = [];
        this.children.each(function (i, e) {
            memento.operations.push({
                text: e.figure.getText(),
                id: e.figure.id
            });
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

        if (typeof memento.operations !== "undefined") {
            $.each(memento.operations, $.proxy(function (i, e) {
                var operation = this.addOperation(e.text);
                operation.id = e.id;
            }, this));
        }

        return this;
    }

});
