package com.alexlander.reggie.service.impl;

import com.alexlander.reggie.entity.AddressBook;
import com.alexlander.reggie.mapper.AddressBookMapper;
import com.alexlander.reggie.service.AddressBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
