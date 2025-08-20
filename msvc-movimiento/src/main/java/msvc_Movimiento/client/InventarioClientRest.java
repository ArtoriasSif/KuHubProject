package msvc_Movimiento.client;

import msvc_Movimiento.dtos.InventarioDTO;
import msvc_Movimiento.dtos.InventarioUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "msvc-Inventario", url = "http://localhost:8085/api/v1/inventario")
public interface InventarioClientRest {

    @PutMapping("/{id}/update-total")
    void updateTotalInventario(@PathVariable("id") Long id, @RequestBody InventarioUpdateDTO totalInventario);

    @GetMapping("/{id}")
    InventarioDTO findById(@PathVariable Long id);
}
