package com.example.bloodbankjava;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class RequestService {

    private static final String FILE_PATH ="C:\\Users\\DELL\\Desktop\\BloodBank-java\\src\\main\\java\\com\\example\\bloodbankjava\\Request.txt";
    public List<BloodRequest> getAllRequests() throws IOException {
        List<BloodRequest> requests = new ArrayList<>();
        List<String> lines = FileReaderWriter.readFile(FILE_PATH);
        for (String line : lines) {
            String[] parts = line.split(",");
            requests.add(new BloodRequest(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    parts[2],
                    Integer.parseInt(parts[3]),
                    parts[4],
                    parts[5],
                    java.time.LocalDate.parse(parts[6])
            ));
        }
        return requests;
    }

    public String getAllRequestsFormatted() throws IOException {
        StringBuilder report = new StringBuilder();
        for (BloodRequest request : getAllRequests()) {
            report.append("Request ID: ").append(request.getRequestId())
                    .append(", Blood Type: ").append(request.getBloodType())
                    .append(", Quantity: ").append(request.getQuantity())
                    .append(", Priority: ").append(request.getPriority())
                    .append(", Status: ").append(request.getStatus())
                    .append("\n");
        }
        return report.toString();
    }

    public int getNextRequestId() throws IOException {
        return getAllRequests().stream()
                .mapToInt(BloodRequest::getRequestId)
                .max()
                .orElse(0) + 1;
    }

    public void addRequest(BloodRequest request) throws IOException {
        List<String> lines = FileReaderWriter.readFile(FILE_PATH);
        String newRequest = request.getRequestId() + "," +
                request.getHospitalId() + "," +
                request.getBloodType() + "," +
                request.getQuantity() + "," +
                request.getPriority() + "," +
                request.getStatus() + "," +
                request.getRequestDate();
        lines.add(newRequest);
        FileReaderWriter.writeFile(FILE_PATH, lines);
    }

    public String getRequestStatusForHospital(int hospitalId) throws IOException {
        StringBuilder statusReport = new StringBuilder();
        for (BloodRequest request : getAllRequests()) {
            if (request.getHospitalId() == hospitalId) {
                statusReport.append("Request ID: ").append(request.getRequestId())
                        .append(", Blood Type: ").append(request.getBloodType())
                        .append(", Quantity: ").append(request.getQuantity())
                        .append(", Status: ").append(request.getStatus())
                        .append(", Priority: ").append(request.getPriority())
                        .append("\n");
            }
        }
        return statusReport.length() > 0 ? statusReport.toString() : "No requests found for this hospital.";
    }
}
