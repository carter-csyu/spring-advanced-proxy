package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/v1/orders")
@ResponseBody
public interface OrderControllerV1 {

    @GetMapping
    String order(@RequestParam("itemId") String itemId);

    @GetMapping("/no-log")
    String noLog();
}
