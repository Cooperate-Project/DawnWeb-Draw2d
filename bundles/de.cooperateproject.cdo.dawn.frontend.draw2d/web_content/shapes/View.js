/**
 * This is a custom version of the draw2d canvas to enable own policies and custom drop behavior.
 */
View = draw2d.Canvas.extend({

    init: function (id, legacyMode) {
        this._super(id);
        this.legacyMode = legacyMode;

        // Policies to show lines
        this.installEditPolicy(new draw2d.policy.canvas.SnapToCenterEditPolicy);
        this.installEditPolicy(new draw2d.policy.canvas.SnapToGeometryEditPolicy);
        this.installEditPolicy(new draw2d.policy.canvas.SnapToInBetweenEditPolicy);

        // Custom Decoration and Connections
        this.installEditPolicy(new draw2d.policy.canvas.FadeoutDecorationPolicy());
        this.installEditPolicy(new draw2d.policy.connection.DragConnectionCreatePolicy({
            createConnection: function () {
                return new RichConnection();
            }
        }));

    },

    onDrop: function (droppedDomNode, x, y, shiftKey, ctrlKey) {
        // Creates an new shape on drop
        var type = $(droppedDomNode).data("shape");
        var figure = eval("new " + type + "();");

        // TODO: Remove prototype implementation
        if (type == "ClassShape" && !this.legacyMode) {
            Draw2DViewer.createNewClass("New Class", x, y);
        }

        // create a command for the undo/redo support
        var command = new draw2d.command.CommandAdd(this, figure, x, y);
        this.getCommandStack().execute(command);
    }
});

