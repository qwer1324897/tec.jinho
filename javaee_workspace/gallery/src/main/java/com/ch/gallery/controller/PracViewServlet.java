package com.ch.gallery.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PracViewServlet extends HttpServlet {
    
    private String uploadPath = "c:\\prac_upload"; 

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 1. 요청한 파일 이름 받기
        String fileName = req.getParameter("name");
        
        if(fileName == null || fileName.trim().equals("")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 2. 파일 객체 생성
        File file = new File(uploadPath, fileName);
        
        if(!file.exists()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND); // 파일 없으면 404 에러
            return;
        }

        // 3. MIME 타입(이미지 종류) 알아내기
        String mimeType = getServletContext().getMimeType(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        resp.setContentType(mimeType);

        // 4. 브라우저가 파일을 바로 보여주도록 헤더 설정 (다운로드 말고 inline)
        resp.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        resp.setHeader("Content-Length", String.valueOf(file.length()));

        // 5. 파일 데이터를 읽어서 브라우저로 쏘기 (NIO 방식)
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}