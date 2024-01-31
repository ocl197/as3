package com.example.assignment3;



import com.example.assignment3.Data.ChannelInfo;

import org.junit.Test;

import static org.junit.Assert.*;


public class ChannelInfoTest {

    @Test
    public void testGetTitle() {
        // Arrange
        String expectedTitle = "Sample Channel";
        ChannelInfo channelInfo = new ChannelInfo(expectedTitle, "1000", "50");

        // Act
        String actualTitle = channelInfo.getTitle();

        // Assert
        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void testGetViewCount() {
        // Arrange
        String expectedViewCount = "1000";
        ChannelInfo channelInfo = new ChannelInfo("Sample Channel", expectedViewCount, "50");

        // Act
        String actualViewCount = channelInfo.getViewCount();

        // Assert
        assertEquals(expectedViewCount, actualViewCount);
    }

    @Test
    public void testGetVideoCount() {
        // Arrange
        String expectedVideoCount = "50";
        ChannelInfo channelInfo = new ChannelInfo("Sample Channel", "1000", expectedVideoCount);

        // Act
        String actualVideoCount = channelInfo.getVideoCount();

        // Assert
        assertEquals(expectedVideoCount, actualVideoCount);
    }

}
