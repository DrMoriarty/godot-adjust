#include "adjust.h"

#import <Foundation/Foundation.h>
#import <AdjustSdk/Adjust.h>

AdjustSdk *AdjustSdk::instance = NULL;

void AdjustSdk::_bind_methods() {
	ClassDB::bind_method(D_METHOD("init"), &AdjustSdk::init);
	ClassDB::bind_method(D_METHOD("trackEvent"), &AdjustSdk::trackEvent);
	ClassDB::bind_method(D_METHOD("trackRevenue"), &AdjustSdk::trackRevenue);
}

AdjustSdk *AdjustSdk::get_singleton() {
	return instance;
}

AdjustSdk::AdjustSdk() {
	ERR_FAIL_COND(instance != NULL);
	instance = this;
}


AdjustSdk::~AdjustSdk() {
}

void AdjustSdk::init(String token, bool production) {
    NSString *appToken = [NSString stringWithCString:token.utf8().get_data() encoding: NSUTF8StringEncoding];
    NSString *environment = production ? ADJEnvironmentProduction : ADJEnvironmentSandbox;
    ADJConfig *adjustConfig = [ADJConfig configWithAppToken:appToken
                                                environment:environment
                                      allowSuppressLogLevel:NO];
    adjustConfig.logLevel = production ? ADJLogLevelError : ADJLogLevelVerbose;
    [Adjust appDidLaunch:adjustConfig];
}

void AdjustSdk::trackEvent(String event) {
    NSString *evToken = [NSString stringWithCString:event.utf8().get_data() encoding: NSUTF8StringEncoding];
    ADJEvent *ev = [ADJEvent eventWithEventToken:evToken];
    [Adjust trackEvent:ev];
}

void AdjustSdk::trackRevenue(String event, double revenue, String currency) {
    NSString *evToken = [NSString stringWithCString:event.utf8().get_data() encoding: NSUTF8StringEncoding];
    NSString *curr = [NSString stringWithCString:currency.utf8().get_data() encoding: NSUTF8StringEncoding];
    ADJEvent *ev = [ADJEvent eventWithEventToken:evToken];
    [ev setRevenue:revenue currency:curr];
    [Adjust trackEvent:ev];
}
