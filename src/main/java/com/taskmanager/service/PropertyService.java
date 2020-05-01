package com.taskmanager.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    @Value("${server.host_port}")
    private @NotNull String server;

    @Value("${server.mapping.user}")
    private @NotNull String userMapping;

    @Value("${server.mapping.project}")
    private @NotNull String projectMapping;

    @Value("${server.mapping.task}")
    private @NotNull String taskMapping;

    @Value("${server.request.list}")
    private @NotNull String requestList;

    @Value("${server.request.list.status.asc}")
    private @NotNull String requestListStatusAsc;

    @Value("${server.request.list.status.desc}")
    private @NotNull String requestListStatusDesc;

    @Value("${server.request.create}")
    private @NotNull String requestCreate;

    @Value("${server.request.update}")
    private @NotNull String requestUpdate;

    @Value("${server.request.delete}")
    private @NotNull String requestDelete;

    @Value("${server.request.view}")
    private @NotNull String requestView;

    @NotNull
    public String getServer() {
        return server;
    }

    @NotNull
    public String getProjectMapping() {
        return projectMapping;
    }

    @NotNull
    public String getTaskMapping() {
        return taskMapping;
    }

    @NotNull
    public String getRequestList() {
        return requestList;
    }

    @NotNull
    public String getRequestListStatusAsc() {
        return requestListStatusAsc;
    }

    @NotNull
    public String getRequestListStatusDesc() {
        return requestListStatusDesc;
    }

    @NotNull
    public String getRequestCreate() {
        return requestCreate;
    }

    @NotNull
    public String getRequestUpdate() {
        return requestUpdate;
    }

    @NotNull
    public String getRequestDelete() {
        return requestDelete;
    }

    @NotNull
    public String getRequestView() {
        return requestView;
    }

    public String getUserMapping() {
        return userMapping;
    }
}
