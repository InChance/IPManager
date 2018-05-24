package com.leo.manager;

import com.leo.model.MaskModel;

import java.util.List;

public interface IMaskManager {

    MaskModel get(String netIp);

    void add(MaskModel model);

    List<MaskModel> getAll();

    void delete(String mask);
}
