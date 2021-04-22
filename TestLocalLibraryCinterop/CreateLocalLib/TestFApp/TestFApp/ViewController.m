//
//  ViewController.m
//  TestFApp
//
//  Created by yangyong on 2021/4/22.
//

#import "ViewController.h"
#import <FB/BBB.h>
@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    NSLog(@"B:::%@",[BBB new].funB);
    NSLog(@"BCallA:::%@",[BBB new].funBCallA);
    // Do any additional setup after loading the view.
}


@end
