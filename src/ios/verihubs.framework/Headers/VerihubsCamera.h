//
//  VerihubsCamera.h
//  opencve
//
//  Created by Verihubs on 13/07/20.
//  Copyright © 2020 Verihubs. All rights reserved.
//

#ifndef VerihubsCamera_h
#define VerihubsCamera_h


#ifdef __cplusplus
#import <opencv2/opencv.hpp>
#endif

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>


@protocol VerihubsCameraDelegate <NSObject>
- (void) changeInstruction: (int) current_instruction instr: (int)instr realPerson: (int)realPerson;
- (void) swiftSaveImg:(UIImage*) image current_instruction:(int)current_instruction;
@end

// Public interface for camera. ViewController only needs to init, start and stop.
@interface VerihubsCamera : NSObject

-(id) initWithController: (UIViewController<VerihubsCameraDelegate>*)c andImageView: (UIImageView*)iv;
-(void)setInstruction: (int)total_instruction instruction:(int*) instruction;
-(void)start;
-(void)stop;
#ifdef __cplusplus
-(UIImage*)UIImageFromMat2: (cv::Mat)image;
#endif
@end


#endif /* VerihubsCamera_h */
