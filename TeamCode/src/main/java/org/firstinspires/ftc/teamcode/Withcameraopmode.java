package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

@TeleOp(name="Demo: One Motor Control with Camera", group="Linear OpMode")
public class Withcameraopmode extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null; //object for left side motor
    private DcMotor rightDrive = null; //object for right drive motor
    private Servo leftServo; //object for left servo
    private Servo rightServo; //object for right servo

    // Camera stream member
    private VisionPortal visionPortal; //object for vision, this takes in the camera, format, and view

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize hardware
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive"); //naming left motor
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive"); //naming right motor
        leftServo = hardwareMap.servo.get("leftServo"); //naming left servo
        rightServo = hardwareMap.servo.get("rightServo"); //naming right servo

        // Initialize webcam
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1"); //camera object

        visionPortal = new VisionPortal.Builder() //settings for our vision
                .setCamera(webcamName) //using Webcam 1
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)  // Try MJPEG if you have lag, format
                .enableLiveView(true)
                .build();

        // Set initial servo positions
        leftServo.setPosition(0); //these are the postion our robot starts in
        rightServo.setPosition(180);

        // Motor directions
        leftDrive.setDirection(DcMotor.Direction.REVERSE); //motors face oppostie directions so need to program them to be reverse
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            double leftPower; //variable for power of left motor
            double rightPower; //variable for power of right motor

            if (gamepad1.x) { //if x is clicked, servos rotate forward
                leftServo.setPosition(0);
                rightServo.setPosition(180);
            } else if (gamepad1.a) { //if a is clicked, servos rotate backwards
                leftServo.setPosition(180);
                rightServo.setPosition(0);
            }

            double drive = -gamepad1.left_stick_y; 
            double turn  =  gamepad1.right_stick_x;
            leftPower    = Range.clip(drive + turn, -1.0, 1.0); //these variables combine inputs from left and right stick to give a number for how much power each motor should recieve
            rightPower   = Range.clip(drive - turn, -1.0, 1.0);

            leftDrive.setPower(leftPower); //setting power of left motor to the variable leftpower
            rightDrive.setPower(rightPower); //setting power of right motor to the variable rightpower

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.addData("Camera State", visionPortal.getCameraState());
            telemetry.update();
        }
    }
}
