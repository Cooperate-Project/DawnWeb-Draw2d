/**
 * Provides utility methods to work with the draw2d canvas (view).
 * @type {{getJSON: Draw2dUtils.getJSON, setJSON: Draw2dUtils.setJSON, setParentInformation: Draw2dUtils.setParentInformation, moveParentsBack: Draw2dUtils.moveParentsBack}}
 */
var Draw2dUtils = {

    /**
     * Returns the serialized version of all displayed figures.
     * @param view a draw2d canvas
     * @returns {string}
     */
    getJSON: function (view) {

        // Iterate over figures to insert parent relating information for easier model traversion
        this.setParentInformation(view);

        // Write JSON
        var writer = new draw2d.io.json.Writer();
        var doc = "";
        writer.marshal(view, function (json) {
            doc = json;
        });
        return doc;
    },

    /**
     * Adds all elements from the json to the provided canvas (view).
     * @param view a draw2d canvas
     * @param json a properly serialized version of draw2d figures
     */
    setJSON: function (view, json) {

        // Read JSON
        var reader = new draw2d.io.json.Reader();
        reader.unmarshal(view, json);

        // Fix z order by recursively moving parent figures backwards
        $.each(json, function (i, o) {
            Draw2dUtils.moveParentsBack(view.getFigure(o.id), view);
        });
    },

    /**
     * A utility method to insert parent information for z-order.
     * @param view the draw2d canvas
     */
    setParentInformation: function (view) {
        var figures = view.getFigures().data;
        $.each(figures, function (i, p) {
            if (p.NAME == "PackageShape") {
                $.each(p.getAboardFigures(true).data, function (i, o) {
                        view.getFigure(o.getId()).parentFigure = p.getId();
                    }
                );
            }
        });
    },

    /**
     * A utility method to correct the z-order of parent elements after de-serialization.
     * @param figure a child figure
     * @param view a draw2d canvas
     */
    moveParentsBack: function (figure, view) {
        if (figure != null) {
            figure.toBack();

            if (figure.parentFigure != null) {
                Draw2dUtils.moveParentsBack(view.getFigure(figure.parentFigure));
            }
        }
    }
};