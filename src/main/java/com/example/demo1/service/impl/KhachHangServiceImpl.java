package com.example.demo1.service.impl;

import com.example.demo1.model.KhachHang;
import com.example.demo1.repository.KhachHangRepository;
import com.example.demo1.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhachHangServiceImpl implements KhachHangService {
    @Autowired
    private KhachHangRepository khRepo;

    @Override
    public List<KhachHang> getAll(Pageable pageable) {
        return khRepo.findAll(pageable).getContent();
    }

    @Override
    public KhachHang getOne(Long id) {
        return khRepo.findById(id).orElse(null);
    }

    @Override
    public KhachHang add(KhachHang kh) {
        return khRepo.save(kh);
    }

    @Override
    public KhachHang update(KhachHang kh) {
        return khRepo.save(kh);
    }

    @Override
    public void delete(Long id) {
        khRepo.deleteById(id);
    }
}
