/**
 * The controller of the draw2d graphical editor view.
 * @type {{init: Draw2DViewer.init, refreshDiagramData: Draw2DViewer.refreshDiagramData, getVersionFromProject: Draw2DViewer.getVersionFromProject, changeStatus: Draw2DViewer.changeStatus, createNewClass: Draw2DViewer.createNewClass, changeName: Draw2DViewer.changeName}}
 */
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

    /**
     * Check with change timestamp if there is a new version of the diagram on the server.
     */
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

    /**
     * Returns the last change timestamp.
     * @returns {*}
     */
    getVersionFromProject: function () {
        var _viewer = this;
        return DawnWeb.getClient().then(function (server) {
            return server.apis.diagram.getLastChanged({projectId: _viewer.project, modelId: _viewer.model});
        });
    },

    /**
     * Changes the status message.
     * @param status a status message (html)
     */
    changeStatus: function (status) {
        $("#statusInfo").html(status);
    },

    /**
     * Calls the server to create a new class.
     * @param className the name of the class
     * @param x absolute x-position
     * @param y absolute y-position
     * @returns {*}
     */
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
            // FIXME: ID is not correctly synced right now (workaroudn: site must be refreshed after call)
        });
    },

    /**
     * Changes the name of a class
     * @param uuid the unique id of the class
     * @param value the new name
     * @returns {*}
     */
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