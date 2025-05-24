package me.zort.acs.http.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HttpException {

    // Common errors
    NODE_NOT_FOUND(
            400, 1001, 1,
            "error.node-not-found"),
    SUBJECT_NOT_FOUND(
            404, 1002, 1,
            "error.subject-not-found"),
    SUBJECT_TYPE_NOT_FOUND(
            400, 1003, 1,
            "error.subject-type-not-found"),
    NODE_NOT_APPLICABLE_ON_SUBJECT_TYPE(
            400, 1004, 2,
            "error.node-not-applicable-on-subject-type"),

    // Controller errors
    CONTROLLER_LIST_NODES_QUERY_NOT_APPLICABLE(
            400, 2001, 1,
            "error.controller.list-nodes.query-not-applicable");

    private final int statusCode;
    private final int errorCode;
    private final int requiredArgumentCount;
    private final String message;
}
