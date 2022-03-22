package mybot.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/boss")
public class BossController {
    public BossService bossService;

    public BossController(BossService bossService) {
        this.bossService = bossService;
    }

    @PostMapping("/create")
    public String createBoss(@RequestBody Boss boss) throws InterruptedException, ExecutionException {
        return bossService.createBoss(boss);
    }

    @GetMapping("/get")
    public Boss getBoss(@RequestParam String documentId) throws InterruptedException, ExecutionException {
        return bossService.getBoss(documentId);
    }

    @PutMapping("/update")
    public String updateBoss(@RequestBody Boss boss) throws InterruptedException, ExecutionException {
        return bossService.updateBoss(boss);
    }

    @PutMapping("/delete")
    public String deleteBoss(@RequestParam String documentId) throws InterruptedException, ExecutionException {
        return bossService.deleteBoss(documentId);
    }

    @GetMapping("/getAll")
    public List<Boss> getAllBoss() throws InterruptedException, ExecutionException {
        return bossService.getAllBoss();
    }

    @GetMapping("test")
    public ResponseEntity<String> testGetEndpoint() {
        return ResponseEntity.ok("Test get endpoint is working");
    }
}
