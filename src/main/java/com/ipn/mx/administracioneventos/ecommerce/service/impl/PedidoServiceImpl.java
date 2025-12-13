package com.ipn.mx.administracioneventos.ecommerce.service.impl;

import com.ipn.mx.administracioneventos.ecommerce.domain.Carrito;
import com.ipn.mx.administracioneventos.ecommerce.domain.CarritoItem;
import com.ipn.mx.administracioneventos.ecommerce.domain.Pedido;
import com.ipn.mx.administracioneventos.ecommerce.domain.PedidoDetalle;
import com.ipn.mx.administracioneventos.ecommerce.domain.Producto;
import com.ipn.mx.administracioneventos.ecommerce.repository.CarritoItemRepository;
import com.ipn.mx.administracioneventos.ecommerce.repository.CarritoRepository;
import com.ipn.mx.administracioneventos.ecommerce.repository.PedidoDetalleRepository;
import com.ipn.mx.administracioneventos.ecommerce.repository.PedidoRepository;
import com.ipn.mx.administracioneventos.ecommerce.repository.ProductoRepository;
import com.ipn.mx.administracioneventos.ecommerce.repository.UsuarioRepository;
import com.ipn.mx.administracioneventos.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {
    @Autowired
    private CarritoRepository carritoRepo;
    @Autowired
    private CarritoItemRepository itemRepo;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private ProductoRepository productoRepo;
    @Autowired
    private PedidoRepository pedidoRepo;
    @Autowired
    private PedidoDetalleRepository detalleRepo;

    @Override
    @Transactional
    public Pedido generarPedido(Long idUsuario) {
        Carrito carrito = carritoRepo.findByUsuario_IdUsuarioAndActivoTrue(idUsuario).orElseThrow();
        Pedido pedido = Pedido.builder()
                .usuario(usuarioRepo.findById(idUsuario).orElseThrow())
                .fechaPedido(LocalDateTime.now())
                .total(BigDecimal.ZERO)
                .detalles(new ArrayList<>())
                .build();
        pedido = pedidoRepo.save(pedido);

        BigDecimal total = BigDecimal.ZERO;
        for (CarritoItem ci : new ArrayList<>(carrito.getItems())) {
            Producto p = productoRepo.findById(ci.getProducto().getIdProducto()).orElseThrow();
            p.setStock(p.getStock() - ci.getCantidad());
            productoRepo.save(p);

            PedidoDetalle pd = PedidoDetalle.builder()
                    .pedido(pedido)
                    .producto(ci.getProducto())
                    .cantidad(ci.getCantidad())
                    .precioUnitario(ci.getPrecioUnitario())
                    .subtotal(ci.getSubtotal())
                    .build();
            detalleRepo.save(pd);
            total = total.add(ci.getSubtotal());
            itemRepo.deleteById(ci.getId());
        }
        carrito.setActivo(false);
        carritoRepo.save(carrito);

        pedido.setTotal(total);
        return pedidoRepo.save(pedido);
    }

    @Override
    public List<Pedido> listar() {
        return pedidoRepo.findAll();
    }

    @Override
    public Pedido obtener(Long idPedido) {
        return pedidoRepo.findById(idPedido).orElse(null);
    }

    @Override
    public List<Pedido> listarPorUsuario(Long idUsuario) {
        return pedidoRepo.findByUsuario_IdUsuario(idUsuario);
    }

    @Override
    public BigDecimal total(Long idPedido) {
        Pedido p = pedidoRepo.findById(idPedido).orElseThrow();
        return p.getTotal();
    }

    @Override
    public byte[] factura(Long idPedido) {
        Pedido p = pedidoRepo.findById(idPedido).orElseThrow();
        StringBuilder sb = new StringBuilder();
        sb.append("FACTURA PEDIDO ").append(p.getIdPedido()).append("\n");
        sb.append("Fecha: ").append(p.getFechaPedido()).append("\n");
        sb.append("Usuario: ").append(p.getUsuario().getNombre()).append("\n\n");
        sb.append("Items:\n");
        for (PedidoDetalle d : p.getDetalles()) {
            sb.append("- ").append(d.getProducto().getNombre())
              .append(" x ").append(d.getCantidad())
              .append(" @ ").append(d.getPrecioUnitario())
              .append(" = ").append(d.getSubtotal()).append("\n");
        }
        sb.append("\nTOTAL: ").append(p.getTotal()).append("\n");
        return sb.toString().getBytes();
    }
}

