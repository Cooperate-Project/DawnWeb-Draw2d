var Draw2DViewer = {
    init: function () {

        // Fill meta info
        this.project = $.urlParam('project');
        this.model = $.urlParam('model');
        $("#projectinfo").html(this.project);
        $("#modelinfo").html(this.model);

        this.canvas = new View("canvas");

        // Get initial json
        DawnWeb.getClient().then(function (server) {
            return server.apis.browse.getProjects();
        })
            .then(function (result) {

                // Fill server connection info
                $("#serverconnect").html("TRUE").attr("class", "boldgreen");
            });
    }
}