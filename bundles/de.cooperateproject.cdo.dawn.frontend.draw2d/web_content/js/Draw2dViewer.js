var Draw2DViewer = {
    init: function () {

        // Fill meta info
        this.project = $.urlParam('project');
        this.model = $.urlParam('model');
        $("#projectinfo").html(this.project);
        $("#modelinfo").html(this.model);

        this.canvas = new View("canvas");

        // Get initial json
        var _viewer = this;
        DawnWeb.getClient().then(function (server) {
            return server.apis.draw2d.getClasses({projectId: _viewer.project, modelId: _viewer.model});
        })
            .then(function (result) {

                // Fill server connection info
                $("#serverconnect").html("TRUE").attr("class", "boldgreen");

                // Insert json
                console.log(result.obj);
                Draw2dUtils.setJSON(_viewer.canvas, result.obj);
            });
    }
}