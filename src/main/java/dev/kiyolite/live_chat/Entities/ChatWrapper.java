/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package dev.kiyolite.live_chat.Entities;

/**
 *
 * @author soyky
 */
public record ChatWrapper(long chatId , int unreadMessages, String contactName) {

}
