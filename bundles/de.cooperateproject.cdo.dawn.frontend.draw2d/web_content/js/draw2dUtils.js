var Draw2dUtils = {

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

    setJSON: function (view, json) {

        // Read JSON
        var reader = new draw2d.io.json.Reader();
        reader.unmarshal(view, json);

        // Fix z order by recursively moving parent figures backwards
        $.each(json, function (i, o) {
            Draw2dUtils.moveParentsBack(canvas.getFigure(o.id));
        });
    },

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

    moveParentsBack: function (figure) {
        if (figure != null) {
            figure.toBack();

            if (figure.parentFigure != null) {
                Draw2dUtils.moveParentsBack(canvas.getFigure(figure.parentFigure));
            }
        }
    }
};