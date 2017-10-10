var Draw2DViewer = {
    init: function () {

        // Fill meta info
        this.project = $.urlParam('project');
        this.model = $.urlParam('model');
        this.lastChanged = 0;
        $("#projectinfo").html(this.project);
        $("#modelinfo").html(this.model);

        this.canvas = new View("canvas");

        // Get initial json
        var _viewer = this;
        DawnWeb.getClient().then(function (server) {
            return server.apis.draw2d.getPackages({projectId: _viewer.project, modelId: _viewer.model});
        })
            .then(function (result) {

                // Fill server connection info
                $("#serverconnect").html("TRUE").attr("class", "boldgreen");

                // Insert json
                console.log(result.obj);
                Draw2dUtils.setJSON(_viewer.canvas, result.obj);

                // Get classes now
                DawnWeb.getClient().then(function (server) {
                    return server.apis.draw2d.getClasses({projectId: _viewer.project, modelId: _viewer.model});
                })
                    .then(function (result) {
                        // Insert json
                        console.log(result.obj);
                        Draw2dUtils.setJSON(_viewer.canvas, result.obj);

                        // Last: Get connections
                        DawnWeb.getClient().then(function (server) {
                            return server.apis.draw2d.getConnections({
                                projectId: _viewer.project,
                                modelId: _viewer.model
                            });
                        })
                            .then(function (result) {
                                // Insert json
                                console.log(result.obj);
                                Draw2dUtils.setJSON(_viewer.canvas, result.obj);

                                // Get initial timestamp
                                DawnWeb.getClient().then(function (server) {
                                    return server.apis.diagram.getLastChanged({
                                        projectId: _viewer.project,
                                        modelId: _viewer.model
                                    });
                                })
                                    .then(function (result) {

                                        _viewer.lastChanged = result.text;

                                        // Start auto refreshing
                                        window.setInterval(function () {
                                            _viewer.refreshDiagramData();
                                        }, 500);

                                    });


                            });
                    });
            });

    },

    refreshDiagramData: function () {
        var _viewer = this;
        if (this.project != undefined && this.model != undefined) {
            _viewer.getVersionFromProject().then(function (result) {

                if (_viewer.lastChanged != result.text) {
                    _viewer.lastChanged = result.text;

                    _viewer.changeStatus('The resource has changed by another user. Please reload the window to apply the changes.');

                }

            });
        }
    },

    getVersionFromProject: function () {
        var _viewer = this;
        return DawnWeb.getClient().then(function (server) {
            return server.apis.diagram.getLastChanged({projectId: _viewer.project, modelId: _viewer.model});
        });
    },

    changeStatus: function (status) {
        $("#statusInfo").html(status);
    },

    createNewClass: function (className, x, y) {
        var _viewer = this;
        return DawnWeb.getClient().then(function (server) {
            return server.apis.diagram.addClass({
                projectId: _viewer.project,
                modelId: _viewer.model,
                className: className,
                x: x,
                y: y
            });
        });
    },

    changeName: function (uuid, value) {
        var _viewer = this;
        return DawnWeb.getClient().then(function (server) {
            return server.apis.diagram.changeFeature({
                projectId: _viewer.project,
                modelId: _viewer.model,
                uuid: uuid,
                featureId: 5,
                value: value
            });
        });
    }
}