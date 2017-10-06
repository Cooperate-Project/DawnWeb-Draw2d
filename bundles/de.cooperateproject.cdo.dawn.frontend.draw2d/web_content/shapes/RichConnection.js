var RichConnection = draw2d.Connection.extend({

    NAME: "RichConnection",
    init: function (attr) {

        this._super(attr);

        this.decorationType = null;
        this.directionSourceToTarget = true;

        this.setRouter(new draw2d.layout.connection.DirectRouter());
        this.setStroke(2);
        this.setRadius(0);
        this.setColor('#333333');
        this.setDecoration("connection");

        this.label = new draw2d.shape.basic.Label({
            text: "",
            stroke: 0,
            editor: new draw2d.ui.LabelEditor()
        });

        this.add(this.label, new draw2d.layout.locator.ParallelMidpointLocator);
    },

    onContextMenu: function (x, y) {
        $.contextMenu({
            selector: 'body',
            events: {
                hide: function () {
                    $.contextMenu('destroy');
                }
            },
            callback: $.proxy(function (key, options) {
                if (key == "connection" || key == "association" || key == "inheritance" || key == "realization"
                    || key == "dependency" || key == "aggregation" || key == "composition") {
                    this.setDecoration(key);
                }
                switch (key) {
                    case "direction":
                        this.changeDirection();
                        break;
                    case "delete":
                        var cmd = new draw2d.command.CommandDelete(this);
                        this.getCanvas().getCommandStack().execute(cmd);
                        break;
                    case "editLabel":
                        this.label.onDoubleClick();
                        break;
                    case "deleteLabel":
                        this.label.setText("");
                        break;
                    default:
                        break;
                }

            }, this),
            x: x,
            y: y,
            items: {
                "direction": {name: "Change direction"},
                "delete": {name: "Delete connection"},
                "sep1": "---------",
                "editLabel": {name: "Edit label"},
                "deleteLabel": {name: "Delete label"},
                "sep2": "---------",
                "connection": {name: "Connection"},
                "association": {name: "Association"},
                "inheritance": {name: "Inheritance"},
                "realization": {name: "Realization"},
                "dependency": {name: "Dependency"},
                "aggregation": {name: "Aggregation"},
                "composition": {name: "Composition"}
            }
        });
    },
    changeDirection: function () {
        console.log("Changed direction!");
        this.directionSourceToTarget = !this.directionSourceToTarget;
        var swapElem = this.getSourceDecorator();
        this.setSourceDecorator(this.getTargetDecorator());
        this.setTargetDecorator(swapElem);
    },
    setDecoration: function (style) {
        this.decorationType = style;
        this.directionSourceToTarget = true;

        switch (style) {
            case "association":
                this.setDashArray("");
                this.setSourceDecorator(null);
                this.setTargetDecorator(new draw2d.decoration.connection.SimpleArrowDecorator());
                break;
            case "inheritance":
                this.setDashArray("");
                this.setSourceDecorator(null);
                this.setTargetDecorator(new draw2d.decoration.connection.ArrowDecorator());
                break;
            case "realization":
                this.setDashArray("- ");
                this.setSourceDecorator(null);
                this.setTargetDecorator(new draw2d.decoration.connection.ArrowDecorator());
                break;
            case "dependency":
                this.setDashArray("- ");
                this.setSourceDecorator(null);
                this.setTargetDecorator(new draw2d.decoration.connection.SimpleArrowDecorator());
                break;
            case "aggregation":
                this.setDashArray("");
                this.setSourceDecorator(null);
                this.setTargetDecorator(new draw2d.decoration.connection.DiamondDecorator());
                break;
            case "composition":
                this.setDashArray("");
                this.setSourceDecorator(null);
                this.setTargetDecorator(new draw2d.decoration.connection.DiamondDecorator().setBackgroundColor("#000000"));
                break;
            case "connection":
                this.setDashArray("");
                this.setSourceDecorator(null);
                this.setTargetDecorator(null);
                break;
        }
    },

    getPersistentAttributes: function () {
        var memento = {type: "RichConnection"};

        memento.id = this.id;

        // Code from Connector
        var parentNode = this.getSource().getParent();
        while (parentNode.getParent() !== null) {
            parentNode = parentNode.getParent();
        }
        memento.source = {
            node: parentNode.getId(),
            port: this.getSource().getName()
        };

        parentNode = this.getTarget().getParent();
        while (parentNode.getParent() !== null) {
            parentNode = parentNode.getParent();
        }
        memento.target = {
            node: parentNode.getId(),
            port: this.getTarget().getName()
        };
        // Code Snippet End

        memento.labelText = this.label.getText();
        memento.decorationType = this.decorationType;
        memento.directionSourceToTarget = this.directionSourceToTarget;

        return memento;
    },

    setPersistentAttributes: function (memento) {
        this._super(memento);

        this.label.setText(memento.labelText);
        this.setDecoration(memento.decorationType);

        if (!memento.directionSourceToTarget) {
            this.changeDirection();
        }
        this.directionSourceToTarget = memento.directionSourceToTarget;

        return this;
    }

});
