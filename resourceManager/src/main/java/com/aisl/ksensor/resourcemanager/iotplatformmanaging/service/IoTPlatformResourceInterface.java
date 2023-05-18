package com.aisl.ksensor.resourcemanager.iotplatformmanaging.service;


import java.util.List;

public interface IoTPlatformResourceInterface {


    public boolean createResource(String endpointUri, String resourceUri, String resourceType, String resourceValue);

//    public boolean deleteResource(String endpointUri, String resourceUri);

//    public boolean updateResource(String endpointUri, String resourceUri, String resourceValue);

    public List<String> retrieveResource(String endpointUri, String resourceUri);

}
