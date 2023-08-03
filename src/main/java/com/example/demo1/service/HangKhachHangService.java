package com.example.demo1.service;

import com.example.demo1.model.HangKhachHang;
import com.example.demo1.model.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HangKhachHangService {
    Page<HangKhachHang> getAll(Pageable pageable);

    HangKhachHang getOne(Long id);

    HangKhachHang add(HangKhachHang kh);

    HangKhachHang update(HangKhachHang kh);

    void delete(Long id);

}
