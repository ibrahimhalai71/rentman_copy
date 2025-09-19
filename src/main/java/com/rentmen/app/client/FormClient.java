package com.rentmen.app.client;

import com.rentmen.app.configurations.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "form", url = "http://localhost:9094", configuration = FeignConfig.class)
public interface FormClient {

    @GetMapping("api/form/{jobId}/{serviceProviderId}/files")
    List<String> getFilePaths(@PathVariable Long jobId, @PathVariable Long serviceProviderId);

}
