PackageShape = draw2d.shape.composite.Raft.extend({

    NAME: "PackageShape",

    init: function (attr) {
        this._super(attr);

        this.packageLabel = new draw2d.shape.basic.Label({
            text: "PackageName",
            stroke: 1,
            fontColor: "#000000",
            bgColor: "#f7f7f7",
            padding: 5,
            resizeable: true,
            editor: new draw2d.ui.LabelInplaceEditor()
        });

        this.add(this.packageLabel, new draw2d.layout.locator.XYAbsPortLocator(0, -26));

        this.width = 200;
        this.height = 200;

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
                switch (key) {
                    case "front":
                        this.toFront();
                        break;
                    case "back":
                        this.toBack();
                        break;
                    case "delete":
                        var cmd = new draw2d.command.CommandDelete(this);
                        this.getCanvas().getCommandStack().execute(cmd);
                        break;
                    default:
                        break;
                }

            }, this),
            x: x,
            y: y,
            items: {
                "front": {name: "Move to front"},
                "back": {name: "Move to back"},
                "delete": {name: "Delete package"}
            }
        });
    },
    setName: function (name) {
        this.packageLabel.setText(name);
    },
    getName: function () {
        return this.packageLabel.getText();
    },
    getPersistentAttributes: function () {
        var memento = {type: "PackageShape"};

        memento.x = this.x;
        memento.y = this.y;
        memento.id = this.id;
        memento.height = this.height;
        memento.width = this.width;
        memento.parentFigure = this.parentFigure;

        memento.aboardedFigures = this.getAboardFigures(true).map(function (fig) {
            return fig.getId();
        });

        memento.name = this.packageLabel.getText();

        return memento;
    },

    setPersistentAttributes: function (memento) {
        this._super(memento);

        this.packageLabel.setText(memento.name);
        this.parentFigure = memento.parentFigure;

        // Recalculate package children
        this.getAboardFigures(true);


        return this;
    }

});