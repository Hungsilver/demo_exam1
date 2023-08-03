package com.example.demo1.controller;

import com.example.demo1.model.HangKhachHang;
import com.example.demo1.model.KhachHang;
import com.example.demo1.service.HangKhachHangService;
import com.example.demo1.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class KhachHangController {
    @Autowired
    KhachHangService khService;

    @Autowired
    HangKhachHangService hkhService;

    public final Integer size = 3;

    @GetMapping("/khachhang")
    private ResponseEntity<List<KhachHang>> getAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        List<KhachHang> list = khService.getAll(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/khachhang/{id}")
    private ResponseEntity<KhachHang> getOne(
            @PathVariable("id") Long id) {
        KhachHang kh = khService.getOne(id);
        if (kh == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
        return ResponseEntity.ok(kh);
    }

    @PostMapping("/khachhang")
    private ResponseEntity<KhachHang> add(
            @RequestBody KhachHang kh) {

        Long idHangKH = kh.getHangKhachHang().getMaHang();
        //get object from db
        HangKhachHang hkh = hkhService.getOne(idHangKH);
        // set hang khach hang vao khachhang tu resquest
        kh.setHangKhachHang(hkh);

        KhachHang khSaved = khService.add(kh);
        if (khSaved == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(khSaved);
    }

    @PutMapping("/khachhang/{id}")
    private ResponseEntity<KhachHang> update(
            @RequestBody KhachHang kh,
            @PathVariable("id") Long idKH
    ) {
        Long idHangKH = kh.getHangKhachHang().getMaHang();
        //get object from db
        HangKhachHang hkh = hkhService.getOne(idHangKH);
        // set hang khach hang vao khachhang tu resquest
        kh.setHangKhachHang(hkh);

        KhachHang idKHCheck = khService.getOne(idKH);
        if (idKHCheck == null) {
            return ResponseEntity.notFound().build();
        }
        kh.setMaKhachHang(idKH);

        KhachHang khUpdated = khService.update(kh);

        if (khUpdated == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(khUpdated);
    }

    @DeleteMapping("/khachhang/{id}")
    private ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        khService.delete(id);
        return ResponseEntity.ok().build();
    }


}
