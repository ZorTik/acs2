package me.zort.acs.http.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/v1")
@RestController
public class SubjectsController {

    // TODO: List resources by grants
}
