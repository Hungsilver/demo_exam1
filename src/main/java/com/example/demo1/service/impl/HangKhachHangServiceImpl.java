package com.example.demo1.service.impl;

import com.example.demo1.model.HangKhachHang;
import com.example.demo1.model.KhachHang;
import com.example.demo1.repository.HangKhachHangRepository;
import com.example.demo1.service.HangKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HangKhachHangServiceImpl implements HangKhachHangService {
    @Autowired
    private HangKhachHangRepository hkhRepo;

    @Override
    public List<HangKhachHang> getAll(Pageable pageable) {
        return hkhRepo.findAll(pageable).getContent();
    }

    @Override
    public HangKhachHang getOne(Long id) {
        return hkhRepo.findById(id).orElse(null);
    }

    @Override
    public HangKhachHang add(HangKhachHang kh) {
        return null;
    }

    @Override
    public HangKhachHang update(HangKhachHang kh) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
