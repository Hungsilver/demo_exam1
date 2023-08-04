package com.example.demo1.controller;

import com.example.demo1.model.HangKhachHang;
import com.example.demo1.model.KhachHang;
import com.example.demo1.service.HangKhachHangService;
import com.example.demo1.service.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class KhachHangController {
    @Autowired
    KhachHangService khService;

    @Autowired
    HangKhachHangService hkhService;

    public final Integer size = 8;

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
            @Validated @RequestBody KhachHang kh // bad request 400,
    ) {
        Long idHangKH = kh.getHangKhachHang().getMaHang();
        //get object from db
        HangKhachHang hkh = hkhService.getOne(idHangKH);
        // set hang khach hang vao khachhang tu resquest
        kh.setHangKhachHang(hkh);

        KhachHang khSaved = khService.add(kh);
        if (khSaved == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(khSaved);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleException(MethodArgumentNotValidException exception) {
        Map<String, String> maps = new HashMap<>();
        exception.getBindingResult().getAllErrors()
                .forEach(x -> {
                    String fieldName = ((FieldError) x).getField();
                    String errorMessage = x.getDefaultMessage();
                    maps.put(fieldName, errorMessage);
                });
        return maps;
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
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(khUpdated);
    }

    @DeleteMapping("/khachhang/{id}")
    private ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        khService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/khachhang/locdiem")
    public ResponseEntity<List<KhachHang>> locTheoDiem() {
        List<KhachHang> list = khService.getAll(PageRequest.of(0, 8));
        List<KhachHang> listLocDiem = list.stream()
                .filter(x -> x.getDiemTichLuy() >= 0 && x.getDiemTichLuy() <= 50)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listLocDiem);
    }

    @GetMapping("/khachhang/max")
    public ResponseEntity<KhachHang> diemMax() {
        List<KhachHang> list = khService.getAll(PageRequest.of(0, 8));
        KhachHang max = list.stream().max((a, b) -> a.getDiemTichLuy().compareTo(b.getDiemTichLuy()))
                .get();


        return ResponseEntity.ok(max);
    }

}
