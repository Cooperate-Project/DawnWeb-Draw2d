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
                        case "front":
                            _table.toFront();
                            break;
                        case "back":
                            _table.toBack();
                            break;
                        case "newAttribute":
                            setTimeout(function () {
                                _table.attributeCompartment.addAttribute("").onDoubleClick();
                            }, 10);
                            break;
                        case "newOperation":
                            setTimeout(function () {
                                _table.operationCompartment.addOperation("").onDoubleClick();
                            }, 10);
                            break;
                        case "deleteClass":
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
                    "newAttribute": {name: "New Attribute"},
                    "newOperation": {name: "New Operation"},
                    "sep1": "---------",
                    "front": {name: "Move to front"},
                    "back": {name: "Move to back"},
                    "sep2": "---------",
                    "deleteClass": {name: "Delete Class"}
                }
            });
        });

        this.add(this.classLabel);
        this.add(this.attributeCompartment);
        this.add(this.operationCompartment);

        var hport = this.createPort("hybrid");
        hport.setName("port_" + this.getId());
        hport.setConnectionAnchor(new draw2d.layout.anchor.FanConnectionAnchor(this));

    },

    setName: function (name) {
        this.classLabel.setText(name);

        return this;
    },
    getName: function() {
        return this.classLabel.getText();
    },

    getAttributes: function () {
        return this.attributeCompartment;
    },

    getOperations: function () {
        return this.operationCompartment;
    },

    getPersistentAttributes: function () {
        var memento = {type: "ClassShape"};

        memento.x = this.x;
        memento.y = this.y;
        memento.id = this.id;
        memento.parentFigure = this.parentFigure;

        // Code from Node
        if (this.persistPorts === true) {
            memento.ports = [];
            this.getPorts().each(function (i, port) {
                memento.ports.push($.extend(port.getPersistentAttributes(), {
                    name: port.getName(),
                    port: port.NAME,
                    locator: port.getLocator().NAME
                }));
            });
        }
        // End of Code Snippet

        memento.name = this.classLabel.getText();
        memento.attributeCompartment = this.attributeCompartment.getPersistentAttributes();
        memento.operationCompartment = this.operationCompartment.getPersistentAttributes();

        return memento;
    },

    setPersistentAttributes: function (memento) {
        this._super(memento);

        this.setName(memento.name);
        this.parentFigure = memento.parentFigure;

        // Code from Node
        if (typeof memento.ports !== "undefined") {
            this.persistPorts = true;
            this.resetPorts();
            $.each(memento.ports, $.proxy(function (i, e) {
                var port = eval("new " + e.port + "()");
                var locator = eval("new " + e.locator + "()");
                port.setPersistentAttributes(e);
                this.addPort(port, locator);
                port.setName(e.name);
            }, this));
        }
        // End of Code Snippet

        if (typeof memento.attributeCompartment !== "undefined") {
            this.attributeCompartment.setPersistentAttributes(memento.attributeCompartment);
        }

        if (typeof memento.operationCompartment !== "undefined") {
            this.operationCompartment.setPersistentAttributes(memento.operationCompartment);
        }

        return this;
    }

});
