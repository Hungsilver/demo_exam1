package com.example.demo1.service;

import com.example.demo1.model.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KhachHangService {
    List<KhachHang> getAll(Pageable pageable);

    KhachHang getOne(Long id);

    KhachHang add(KhachHang kh);

    KhachHang update(KhachHang kh);

    void delete(Long id);

}
