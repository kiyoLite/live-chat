/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Record.java to edit this template
 */
package dev.kiyolite.live_chat.Entities;

import dev.kiyolite.live_chat.Enums.WebsocketResponseType;

/**
 *
 * @author soyky
 */
public record WebsocketResponse(WebsocketResponseType responseType, String payload) {

}
