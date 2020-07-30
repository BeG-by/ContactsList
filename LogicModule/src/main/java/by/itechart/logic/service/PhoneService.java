package by.itechart.logic.service;

import by.itechart.logic.entity.Phone;
import by.itechart.logic.exception.ServiceException;

import java.util.List;

public interface PhoneService {

    List<Phone> findByContactId(long contactId) throws ServiceException;

    void saveAll(List<Phone> phoneList) throws ServiceException;

    void deleteByContactId(long contactId) throws ServiceException;
}
