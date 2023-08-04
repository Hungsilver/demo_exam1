package com.example.demo1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "khachhang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maKhachHang;

    @NotBlank(message = "khong duoc de trong ten khach hang")
    private String tenKhachHang;

    @NotBlank(message = "khong duoc de trong sdt")
    private String soDienThoai;

    private Boolean gioiTinh;

    private Integer diemTichLuy;

    @ManyToOne
    @JoinColumn(name = "hangkhachhang")
    private HangKhachHang hangKhachHang;
}
