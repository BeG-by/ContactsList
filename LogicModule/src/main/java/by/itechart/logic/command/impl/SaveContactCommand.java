package by.itechart.logic.command.impl;

import by.itechart.logic.command.Command;
import by.itechart.logic.dto.ContactDTO;
import by.itechart.logic.exception.ServiceException;
import by.itechart.logic.service.ContactService;
import by.itechart.logic.service.impl.ContactServiceImpl;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveContactCommand implements Command {

    private ContactService contactService = new ContactServiceImpl();
    private static final Logger logger = Logger.getLogger(ContactServiceImpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        try {
            final ContactDTO contactDTO = contactService.parseMultipartRequest(req);

            System.out.println(contactDTO.getContact());


            ///--- validation ---

//            contactService.saveContact(contactDTO);

        } catch (ServiceException e) {
            resp.setStatus(resp.SC_BAD_REQUEST);
            resp.getWriter().write(new Gson().toJson(e.getMessage()));
            e.printStackTrace();
        }

//        String s = req.getReader().readLine();
//        final Gson gson = new Gson();
//        System.out.println(s);
//
//        final ContactRequest contactRequest = gson.fromJson(s, ContactRequest.class);
//        System.out.println(contactRequest);
//
//        System.out.println("CONT:" +contactRequest.getContact());
//        System.out.println("ATT:" + contactRequest.getAttachments());
//        System.out.println("COMMENT:" + contactRequest.getComments());
//        System.out.println("IMG:" + contactRequest.getImage());

//        s = s.substring(s.indexOf(",") + 1).trim();
//
//        System.out.println(s);
//
//        final Base64.Decoder decoder = Base64.getDecoder();
//
//        System.out.println(decoder);
//
//        final byte[] decode = decoder.decode(s);
//        FileUtils.writeByteArrayToFile(new File("F:/test"), decode);

        resp.setContentType("application/json");


    }

}
