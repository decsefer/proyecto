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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

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
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 16);
                cs.newLineAtOffset(50, 750);
                cs.showText("FACTURA PEDIDO " + p.getIdPedido());
                cs.setFont(PDType1Font.HELVETICA, 12);
                cs.newLineAtOffset(0, -20);
                cs.showText("Fecha: " + p.getFechaPedido());
                cs.newLineAtOffset(0, -15);
                cs.showText("Usuario: " + p.getUsuario().getNombre());
                cs.newLineAtOffset(0, -25);
                cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
                cs.showText("Items:");
                cs.setFont(PDType1Font.HELVETICA, 12);
                for (PedidoDetalle d : p.getDetalles()) {
                    cs.newLineAtOffset(0, -15);
                    cs.showText(d.getProducto().getNombre()
                            + " x " + d.getCantidad()
                            + " @ " + d.getPrecioUnitario()
                            + " = " + d.getSubtotal());
                }
                cs.newLineAtOffset(0, -25);
                cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
                cs.showText("TOTAL: " + p.getTotal());
                cs.endText();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            doc.save(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error al generar PDF de factura", e);
        }
    }
}

