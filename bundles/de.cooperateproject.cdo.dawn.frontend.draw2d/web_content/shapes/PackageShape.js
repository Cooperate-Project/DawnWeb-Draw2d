PackageShape = draw2d.shape.composite.Raft.extend({

    NAME: "PackageShape",

    init: function (attr) {
        this._super(attr);

        this.packageLabel = new draw2d.shape.basic.Label({
            text: "Package",
            stroke: 1,
            fontColor: "#000000",
            bgColor: "#f7f7f7",
            padding: 5,
            resizeable: true,
            editor: new draw2d.ui.LabelInplaceEditor()
        });

        // Add top left package label
        this.add(this.packageLabel, new draw2d.layout.locator.XYAbsPortLocator(0, -26));

        this.width = 150;
        this.height = 100;

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

        // Add ids of child figures
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

        // Recalculate package children (true adds them to the list)
        this.getAboardFigures(true);


        return this;
    }

});
