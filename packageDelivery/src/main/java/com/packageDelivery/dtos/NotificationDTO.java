/**
 * 
 */
package com.packageDelivery.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Acer
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class NotificationDTO {
    private String notiData;
    private String redirectUrl;
    private boolean readed;
    private int userId;
    private String role;
}
